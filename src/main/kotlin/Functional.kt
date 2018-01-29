interface TC

interface HKFab<out F,out A>

interface FunctorFab<F> : TC {
    fun <A, B> map(fa: HKFab<F,A>, f: (A) -> B): HKFab<F,B>
}

sealed class OptionFab<out A> {
    object None : OptionFab<Nothing>()

    data class Some<out A>(val value: A) : OptionFab<A>()

    inline infix fun <B> map(f: (A) -> B): OptionFab<B> = when (this) {
        is None -> this
        is Some -> Some(f(value))
    }

}

abstract class TryFab<out A> {
    companion object {
        operator fun <A> invoke(f: () -> A): TryFab<A> {
            return try {
                SuccessFab(f())
            } catch (t: Throwable) {
                return FailureFab(t)
            }
        }
    }

    abstract fun isSuccess(): Boolean
    abstract fun isFailure(): Boolean
    abstract fun get(): A
}

data class SuccessFab<out A>(private val value: A) : TryFab<A>() {
    override fun isSuccess(): Boolean = true

    override fun isFailure(): Boolean = false

    override fun get(): A = value

}

data class FailureFab<out A>(val exception: Throwable) : TryFab<A>() {
    override fun isSuccess(): Boolean = false

    override fun isFailure(): Boolean = true

    override fun get(): A = throw Exception()

}
