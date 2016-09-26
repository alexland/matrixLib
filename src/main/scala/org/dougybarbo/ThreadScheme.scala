

package org.dougybarbo.MatrixLib

import java.util.concurrent.{
	Callable,
	Executors
}

import collection.mutable.ArrayBuffer

/**
 * defines an interface for how to execute functions
 */
trait ThreadScheme {
	/**
	 * 	pass in a fn that returns a value of type A
	 * 	returns a fn that returns a value of type A
	 * 	the actual function may execute on another thread
	 */
	def execute[A](func: Function0[A]): Function0[A]
}

/**
 * companion object to ThreadScheme trait
 */
object ThreadScheme {
	/**
	 * will execute all functions on the local thread
	 */
	object LocalThread extends ThreadScheme {
		def execute[A](func: Function0[A]) = func
	}

	/**
	 * concurrent/multi-threaded option
	 * will execute all functions within a thread pool
	 */
	object MultiThread extends ThreadScheme {
		val pool = Executors.newFixedThreadPool(
			java.lang.Runtime.getRuntime.availableProcessors
		)
		def execute[A](func: Function0[A]) = {
			// submit a callable class to the thread pool
			val future = pool.submit(new Callable[A] {
				def call(): A = {
					Console.println(
						"executing function on thread: " + Thread.currentThread.getName
					)
					func()
				}
			})
			// create a function that will block when called and
			// wait for the defered thread to finish execution
			() ⇒ future.get()
		}
	}
}
