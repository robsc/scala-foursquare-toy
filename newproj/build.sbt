// Set the project name to the string 'My Project'
name := "QR Coding checkins"

// The := method used in Name and Version is one of two fundamental methods.
// The other method is <<=
// All other initialization methods are implemented in terms of these.
version := "0.0.1"


// Add multiple dependencies
libraryDependencies ++= Seq(
    "net.liftweb" %% "lift-webkit" % "2.4-M1" % "compile" withSources,
    "net.liftweb" %% "lift-mapper" % "2.4-M1" % "compile" withSources,
    "net.liftweb" %% "lift-json" % "2.4-M1" % "compile" withSources, 
   "net.liftweb" %% "lift-common" % "2.4-M1" % "compile" withSources, 
   "net.liftweb" %% "lift-util" % "2.4-M1" % "compile" withSources, 
    "org.scalaj" %% "scalaj-collection"  % "1.1", 
    "org.scalaj" %% "scalaj-http" % "0.2.8" % "compile" withSources(),
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test",
    "junit" % "junit" % "4.8.2" % "test",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test",
    "com.h2database" % "h2" % "1.2.138",
    "com.novocode" % "junit-interface" % "0.6" % "test"
)

scalaVersion := "2.8.1"

// Exclude backup files by default.  This uses ~=, which accepts a function of
//  type T => T (here T = FileFilter) that is applied to the existing value.
// A similar idea is overriding a member and applying a function to the super value:
//  override lazy val defaultExcludes = f(super.defaultExcludes)
//
defaultExcludes ~= (filter => filter || "*~")
/*  Some equivalent ways of writing this:
defaultExcludes ~= (_ || "*~")
defaultExcludes ~= ( (_: FileFilter) || "*~")
defaultExcludes ~= ( (filter: FileFilter) => filter || "*~")
*/

// Use the project version to determine the repository to publish to.
publishTo <<= version { (v: String) =>
  if(v endsWith "-SNAPSHOT")
    Some(ScalaToolsSnapshots)
  else
    Some(ScalaToolsReleases)
}