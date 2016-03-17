

to build & run the tests:
this command will print a summary to stdout

```scala
%> sbt coverage test

```

you generate a detailed report of the test coverage like so:

```scala
%> sbt coverageReport

```

from the project root, cd into the reports directory

```shell
cd target/scala-2.11/scoverage-report

```

you'll find several several files there but you can access them all
by opening _index.html_ in a browser.



to build:

```shell
sbt clean assembly

```
then open a scala REPL like so:

```shell
sbt console

```



```scala
scala> import org.dougybarbo.MatrixLib._

scala> // simple implementation of the ThreadStrategy interface that
scala> // executes all work on the current thread

scala> implicit val ts = ThreadStrategy.SameThreadStrategy

scala> val a1 = new Matrix(Array(Array(4, 3, 9), Array(5, 2, 7)))

Matrix = Matrix
|4.0 3.0 9.0|
|5.0 2.0 7.0|

scala> val a2 = new Matrix(Array(Array(4), Array(7), Array(3)))
a2: Matrix = Matrix
|4.0|
|7.0|
|3.0|

scala> MatrixMul.mmul(a1, a2)

Matrix = Matrix
|64.0|
|55.0|


scala> val a2 = MatrixCons.rnd(5, 4)

org.dougybarbo.MatrixLib.Matrix =
Matrix
|0.004794428821382701 0.2620498106355573 0.783384752981363 0.007106572561377922|
|0.8535066782270941 0.023249957160792256 0.7352391529229894 0.8496634819741622|
|0.6886356508530016 0.700685627363455 0.708247588876248 0.5638898455293914|
|0.7092644299091191 0.5506559911556173 0.9469044292508948 0.9534285048920463|
|0.599426613106944 0.9472279925429852 0.6308073776679892 0.6908890500172306|

```

now change the (implicitly) available ThreadStrategy

```scala
scala> implicit val ts = ThreadStrategy.ThreadPoolStrategy

scala> mmul(a1, a2)

executing function on thread: pool-1-thread-1
executing function on thread: pool-1-thread-2
Matrix = Matrix
|64.0|
|55.0|




```
