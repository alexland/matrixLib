
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

name := "matrix-lib"

// assemblyJarName in assembly := "matrixLib.jar"

mainClass in Compile := Some("org.dougybarbo.MatrixLib.Matrix")
// mainClass in assembly := Some("org.dougybarbo.MatrixLib.MatrixMul")

version := "0.1-SNAPSHOT"

organization := "org.dougybarbo"

scalaVersion := "2.11.8"

autoScalaLibrary := true

// crossScalaVersions := Seq("2.11.8", "2.12.0-M5")

javaHome := Some(file("/Library/Java/JavaVirtualMachines/jdk1.8.0_102.jdk/Contents/Home"))

// scalaHome := Some(file("/usr/local/bin/scala"))

resolvers ++= {
	Seq(
		"scalaz-bintray" 					at		"https://dl.bintray.com/scalaz/releases",
		"Local Maven Repository"	at		"file://"+Path.userHome.absolutePath+"/.m2/repository",
		"Sonatype Snapshots"			at		"https://oss.sonatype.org/content/repositories/snapshots/",
 		"Sonatype Releases"				at		"https://oss.sonatype.org/content/repositories/releases/",
		"Artima Maven Repository" at 		"http://repo.artima.com/releases"
	)
}

libraryDependencies ++= {
	Seq(
		"org.scala-lang.modules"			%%		"scala-parser-combinators"	%			"1.0.4",
		"com.github.fommil.netlib"		%			"all" 											%			"1.1.2",
		"net.sourceforge.f2j"					%			"arpack_combined_all"				%			"0.1",
		"net.sf.opencsv"							%			"opencsv"										%			"2.3",
		"com.github.rwl"							%			"jtransforms"								%			"2.4.0",
		"org.apache.commons"					%			"commons-math3"							%			"3.6.1",
		"org.scalanlp"								%%		"breeze-natives"						%			"0.12",
		"org.scalanlp"								%%		"breeze"										%			"0.12",
		// "com.lihaoyi"							%			"ammonite-repl_2.11.8"			% 		"0.5.6", 				%	"test" cross CrossVersion.full,
		"org.spire-math"							%%		"spire"											%			"0.11.0",
		"org.typelevel"								%%		"cats"											%			"0.7.0",
		"com.github.scala-blitz"			%%		"scala-blitz"								%			"1.2",
		"org.scalactic"								%%		"scalactic"									%			"3.0.0",
		"org.scalatest"								%%		"scalatest"									%			"3.0.0"		% "test"
			)
}

// initialCommands += "import akka.testkit._"

// enable the appropriate archetype
enablePlugins(JavaAppPackaging)

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions in (Compile, doc) ++= Seq(
	"-unchecked",
	"-optimize",
	"-Yinline-warnings",
	"-feature",
	"-Yrangepos"
)

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)
  .setPreference(RewriteArrowSymbols, true)
	.setPreference(IndentWithTabs, true)
	.setPreference(IndentPackageBlocks, true)
	.setPreference(DoubleIndentClassDeclaration, true)

coverageEnabled in Test := true

// coverage thresholds that cause the build to fail
coverageMinimum := 10
coverageFailOnMinimum := false

coverageHighlighting := {
	if(scalaBinaryVersion.value == "2.11") true
	else false
}

publishArtifact in Test := false

parallelExecution in Test := false

/**
	*	this setting scoped to the 'Compile' configuration &
	*	the 'unmanagedSources' task
	*/
excludeFilter in (Compile, unmanagedSources) := new FileFilter {
	def accept(f: File) = "/srcAlt/.*".r.pattern.matcher(f.getAbsolutePath).matches
}

seq(Revolver.settings: _*)

fork in run := true

javaOptions in (Compile, run) ++= Seq(
	"-Xincgc",
	"-Xms6g",
	"-Xmx6g",
	"-Xmn2g",
	"-Xss20m",
	"-XX:+AggressiveOpts",
	"-XX:+PrintGCDetails",
	"-XX:+PrintGCDateStamps",
	"-Xloggc:gc_log.gc"
)
