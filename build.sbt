name := """WebSeverTest"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaEbean,
  cache,
  javaWs,
  "org.webjars"    %%   "webjars-play"          % "2.3.0-2",
  "org.webjars"    %    "bootstrap"             % "3.1.1-2",
  "commons-io" % "commons-io" % "2.4"
)

includeFilter in (Assets, LessKeys.less) := "*.less"
//excludeFilter in (Assets, LessKeys.less) := "_*.less"

