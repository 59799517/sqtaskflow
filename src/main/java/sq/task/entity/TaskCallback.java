//package sq.task.entity;
//
//import java.util.Objects;
//import java.util.function.Consumer;
//
///**
// * @Classname TaskCallback
// * @Description 执行结果回调
// * @Version 1.0.0
// * @Date 2023/5/31 10:18
// * @Created by shang
// */
//@FunctionalInterface
//public interface TaskCallback<T,L> {
//
//    /**
//     * Performs this operation on the given argument.
//     *
//     * @param t the input argument
//     */
//    void accept(T t,L l);
//
//    /**
//     * Returns a composed {@code Consumer} that performs, in sequence, this
//     * operation followed by the {@code after} operation. If performing either
//     * operation throws an exception, it is relayed to the caller of the
//     * composed operation.  If performing this operation throws an exception,
//     * the {@code after} operation will not be performed.
//     *
//     * @param after the operation to perform after this operation
//     * @return a composed {@code Consumer} that performs in sequence this
//     * operation followed by the {@code after} operation
//     * @throws NullPointerException if {@code after} is null
//     */
//    default Consumer<T> andThen(Consumer<? super T> after) {
//        Objects.requireNonNull(after);
//        return (T t,L l) -> { accept(t,l); after.accept(t,l); };
//    }
//}
