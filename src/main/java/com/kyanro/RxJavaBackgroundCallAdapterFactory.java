package com.kyanro;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Retrofit 2系では自動的にbackgroundで実行されないようになったため、1系と挙動を合わせるために利用するアダプタ。
 * 主に移行の際の手間を軽減するために利用されることを想定。
 * 新規の場合はできるだけ{@link RxJavaCallAdapterFactory} を利用する。
 */
public final class RxJavaBackgroundCallAdapterFactory extends CallAdapter.Factory {

    public static RxJavaBackgroundCallAdapterFactory create() {
        return new RxJavaBackgroundCallAdapterFactory();
    }

    private RxJavaBackgroundCallAdapterFactory() {
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        final CallAdapter<?> originalCallAdapter = RxJavaCallAdapterFactory.create()
                .get(returnType, annotations, retrofit);
        return new CallAdapter<Observable<?>>() {
            @Override
            public Type responseType() {
                return originalCallAdapter.responseType();
            }

            @Override
            public <R> Observable<R> adapt(Call<R> call) {

                // 元は必ずObservable<R>のはずなので
                @SuppressWarnings("unchecked")
                Observable<R> originalObservable = (Observable<R>) originalCallAdapter.adapt(call);
                if (originalObservable == null) {
                    // TODO: 元がnullだったらどうする？とりあえず元がnullなのでそのままnull を返しておく
                    return null;
                }

                return originalObservable.subscribeOn(Schedulers.io());
            }
        };
    }
}
