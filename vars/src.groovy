def call(def giturl, def branch) {

  // Use println for output in Groovy:
  println "---------- Git URL: $giturl ----------"
  println "---------- Branch: $branch ----------"

  if (giturl.contains("github.com")) {
    checkout([$class: 'GitSCM', branches: [[name: branch]], extensions: [], userRemoteConfigs: [[url: giturl]]])
  } else {
    error("Please provide a valid GitHub URL")
  }
}
