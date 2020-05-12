name := "Lift 3 starter template"

version := "0.1.0"

organization := "com.myco"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  "snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
)

enablePlugins(JettyPlugin)

unmanagedResourceDirectories in Test += baseDirectory.value / "src/main/webapp"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "3.3.0"
  val liftEdition = liftVersion.split("\\.").take(2).mkString(".")
  Seq(
    "net.liftweb"       %% "lift-webkit"            % liftVersion,
    "net.liftweb"       %% "lift-mapper"            % liftVersion,
    "net.liftmodules"  %% "lift-jquery-module_3.0"  % "2.10",
    "ch.qos.logback"    % "logback-classic"         % "1.2.3",
    "org.specs2"        %% "specs2-core"            % "3.9.4"            % "test",
    "com.h2database"    % "h2"                      % "1.4.187",
    "javax.servlet"     % "javax.servlet-api"       % "3.0.1"            % "provided"
  )
}

scalacOptions in Test ++= Seq("-Yrangepos")
