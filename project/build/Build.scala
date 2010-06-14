import sbt._

class Build(info: ProjectInfo) extends DefaultProject(info)
{
	val scalaToolsSnapshots = ScalaToolsSnapshots

	val guice = "com.google.inject" % "guice" % "latest.release"
	val slf4j_api = "org.slf4j" % "slf4j-api" % "latest.release"
	val slf4j_log4j12 = "org.slf4j" % "slf4j-log4j12" % "latest.release" % "runtime"

	val junit = "junit" % "junit" % "latest.release" % "test"
	val scalatest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.RC5-SNAPSHOT"
	val junitInterface = "com.novocode" % "junit-interface" % "latest.release" % "test"

	override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
}
