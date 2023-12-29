def call (def giturl, def branch) {
  echo ""
  echo "---------- Git URL: $(giturl) ----------"
  echo "---------- Git URL: $(branch) ----------"
  echo ""
  if (giturl.contains("github.com"))
  {
  checkout ([$class: 'GitSCM', (branches: [[name: 'branch']], extensions: [], userRemoteConfigs: [[url: 'giturl']]]) 
  }
  else 
  {
    error("please provider valid github")
  }
}
