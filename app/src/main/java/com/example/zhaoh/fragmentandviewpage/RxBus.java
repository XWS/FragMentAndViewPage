package com.example.zhaoh.fragmentandviewpage;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Jiang Chen on 16/2/23.
 */
public class RxBus {

    private static final Subject<Object, Object> BUS = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {
        throw new AssertionError("No instances.");
    }

    public static void send(Object o) {
        BUS.onNext(o);
    }

    public static Observable<Object> toObservable() {
        return BUS;
    }
}
