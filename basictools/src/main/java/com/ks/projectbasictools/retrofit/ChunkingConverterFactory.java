package com.ks.projectbasictools.retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;
import retrofit2.http.Body;

public class ChunkingConverterFactory extends Factory {
    private BufferedSink bufferedSink;
    private final RequestBody requestBody;
    private final UploadListener listener;

    public ChunkingConverterFactory(RequestBody requestBody, UploadListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        boolean isBody = false;
        boolean isChunked = false;
        Annotation[] var7 = parameterAnnotations;
        int var8 = parameterAnnotations.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            Annotation annotation = var7[var9];
            isBody |= annotation instanceof Body;
            isChunked |= annotation instanceof ChunkingConverterFactory.Chunked;
        }

        retrofit.nextRequestBodyConverter(this, type, parameterAnnotations, methodAnnotations);
        return new Converter<Object, RequestBody>() {
            public RequestBody convert(Object value) throws IOException {
                return new RequestBody() {
                    public MediaType contentType() {
                        return ChunkingConverterFactory.this.requestBody.contentType();
                    }

                    public long contentLength() throws IOException {
                        return ChunkingConverterFactory.this.requestBody.contentLength();
                    }

                    public void writeTo(BufferedSink sink) throws IOException {
                        if (ChunkingConverterFactory.this.bufferedSink == null) {
                            ChunkingConverterFactory.this.bufferedSink = Okio.buffer(this.sink(sink));
                        }

                        ChunkingConverterFactory.this.requestBody.writeTo(ChunkingConverterFactory.this.bufferedSink);
                        ChunkingConverterFactory.this.bufferedSink.flush();
                    }

                    private Sink sink(Sink sink) {
                        return new ForwardingSink(sink) {
                            long bytesWritten = 0L;
                            long contentLength = 0L;

                            public void write(Buffer source, long byteCount) throws IOException {
                                super.write(source, byteCount);
                                if (this.contentLength == 0L) {
                                    this.contentLength = contentLength();
                                }

                                this.bytesWritten += byteCount;
                                ChunkingConverterFactory.this.listener.onProgress(this.bytesWritten, this.contentLength, this.bytesWritten == this.contentLength);
                            }
                        };
                    }
                };
            }
        };
    }

    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Chunked {
    }
}
