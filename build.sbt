name := "hadoop"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "3.1.1",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "3.1.1",
  "org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % "3.1.1"
)