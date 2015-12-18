name := "ApacheCMDA_Frontend"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa, 
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "com.google.code.gson" % "gson" % "2.2.2",
  "com.amazonaws" % "aws-java-sdk" % "1.7.6",
  "commons-io" % "commons-io" % "2.4",
  "org.jsoup" % "jsoup" % "1.7.2"
)

play.Project.playJavaSettings

