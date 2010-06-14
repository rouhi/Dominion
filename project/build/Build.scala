import sbt._

class Build(info: ProjectInfo) extends DefaultProject(info)
{
	val slf4jVersion = "1.6.0"

	val scalaToolsSnapshots = ScalaToolsSnapshots

	val guice = "com.google.inject" % "guice" % "2.0"
	val slf4j_api = "org.slf4j" % "slf4j-api" % slf4jVersion
	val slf4j_log4j12 = "org.slf4j" % "slf4j-log4j12" % slf4jVersion

	val junit = "junit" % "junit" % "4.8.1"
	val scalatest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.RC3-SNAPSHOT"
	val junitInterface = "com.novocode" % "junit-interface" % "0.3"

	override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
}
