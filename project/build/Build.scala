import sbt._

class Build(info: ProjectInfo) extends DefaultProject(info)
{
	val scalaToolsSnapshots = ScalaToolsSnapshots

	val junit = "junit" % "junit" % "4.8.1"
	val scalatest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.RC3-SNAPSHOT"
	val junitInterface = "com.novocode" % "junit-interface" % "0.3"

	override def testFrameworks = super.testFrameworks ++ List(new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker"))
}
