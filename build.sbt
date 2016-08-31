
import scalariform.formatter.preferences._

name := "matrix-lib"

assemblyJarName in assembly := "matrixLib.jar"

version := "0.1-SNAPSHOT"

organization := "org.dougybarbo"

scalaVersion := "2.12.0-M5"

javaHome := Some(file("/Library/Java/JavaVirtualMachines/jdk1.8.0_102.jdk/Contents/Home"))

resolvers ++= {
	Seq(
		"scalaz-bintray" 					at		"http://dl.bintray.com/scalaz/releases",
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
		"org.scalanlp"								%%		"breeze"										%			"0.12",
		"org.scalanlp"								%%		"breeze-natives"						%			"0.12",
		// "com.lihaoyi"							%			"ammonite-repl_2.11.8"			% 		"0.5.6", 				%	"test" cross CrossVersion.full,
		"org.spire-math"							%%		"spire"											%			"0.11.0",
		"org.typelevel"								%%		"cats"											%			"0.7.0",
		"com.github.scala-blitz"			%%		"scala-blitz"								%			"1.2",
		"org.scalactic"								%%		"scalactic"									%			"3.0.0",
		"org.scalatest"								%%		"scalatest"									%			"3.0.0"		% "test"
			)
}

// initialCommands += "import akka.testkit._"

enablePlugins(SbtNativePackager)

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions in (Compile, doc) ++= Seq(
			"-unchecked",
			"-optimize",
			"-Yinline-warnings",
			"-feature",
			"-Yrangepos"
)

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)
  .setPreference(RewriteArrowSymbols, true)

coverageEnabled in Test := true

// ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 0

// ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := false

// ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := {
//     if(scalaBinaryVersion.value == "2.11") true
//     else false
// }

publishArtifact in Test := false

parallelExecution in Test := false

/**
*	this setting scoped to the 'Compile' configuration &
*	the 'unmanagedSources' task
*/
excludeFilter in (Compile, unmanagedSources) := new FileFilter {
	def accept(f: File) = "/srcAlt/.*".r.pattern.matcher(f.getAbsolutePath).matches
}

mainClass in assembly := Some("org.dougybarbo.MatrixLib.MatrixMul")

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

seq(Revolver.settings: _*)

assemblyMergeStrategy in assembly := {
	case PathList("javax", "servlet", xs @ _*) 					=>		MergeStrategy.first
	case PathList(ps @ _*) if ps.last endsWith ".html"	=>		MergeStrategy.first
	case "application.conf"															=>		MergeStrategy.concat
	case "unwanted.txt" 																=>		MergeStrategy.discard
	case x 																							=>
		val oldStrategy = (assemblyMergeStrategy in assembly).value
		oldStrategy(x)
}

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
	{
		case "application.conf" 													=>		MergeStrategy.concat
		case "reference.conf" 														=> 		MergeStrategy.concat
		case "META-INF/spring.tooling" 										=>		MergeStrategy.concat
		case "overview.html"															=> 		MergeStrategy.rename
		case PathList("javax", "servlet", xs @ _*)				=>		MergeStrategy.last
		case PathList("org", "apache", xs @ _*) 					=> 		MergeStrategy.last
		case PathList("META-INF", xs @ _*) 								=> 		MergeStrategy.discard
		case PathList("com", "esotericsoftware", xs @ _*) => 		MergeStrategy.last
		case "about.html" 																=> 		MergeStrategy.rename
		case x 																						=>		old(x)
	}
}

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
	cp filter { f =>
		(f.data.getName contains "commons-logging") ||
		(f.data.getName contains "sbt-link")
	}
}
