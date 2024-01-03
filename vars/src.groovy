def call (def giturl, def branch) {
  sh "echo"
  sh "echo ---------- Git URL: ${giturl} ----------"
  sh "echo ---------- Git URL: ${branch} ----------"
  sh "echo"
  if (giturl.contains("github.com"))
  {
  checkout ([$class: 'GitSCM', branches: [[name: branch]], extensions: [], userRemoteConfigs: [[url: giturl]]]) 
  }
  else 
  {
    error("please provider valid github")
  }
}
