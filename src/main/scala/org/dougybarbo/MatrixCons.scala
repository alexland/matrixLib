
package org.dougybarbo.MatrixLib

import java.text.DecimalFormat
import java.util.concurrent.{
	Callable,
	Executors
}

import collection.mutable.ArrayBuffer

import scala.util.{
	Random ⇒ RND
}

object MatrixCons {
	/**
	 *
	 *
	 */
	def rnd(m: Int, n: Int) = {
		val genRow = (m: Int) ⇒ Seq.fill(m)(RND.nextDouble).toArray
		val mx = (m: Int, n: Int, f: Int ⇒ Array[Double]) ⇒ Seq.fill(m)(f(n))
			.toArray
		new Matrix(mx(m, n, genRow))
	}

}
