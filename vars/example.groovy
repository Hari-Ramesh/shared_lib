def mvnHome
def sonarScannerHome

def setupTools() {
    mvnHome = tool name: 'Maven', type: 'hudson.tasks.Maven$MavenInstallation'
    sonarScannerHome = tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
}

def checkoutCode() {
    stage('Checkout') {
        // Checkout your code from version control
        // Use checkout scm for default SCM or specify your SCM step
    }
}

def buildAndTest() {
    stage('Build and Test') {
        sh "${mvnHome}/bin/mvn clean install"
    }
}

def runSonarQubeAnalysis() {
    stage('SonarQube Analysis') {
        sh "${sonarScannerHome}/bin/sonar-scanner"
    }
}

def deployToNexus() {
    stage('Deploy to Nexus') {
        // Deploy artifacts to Nexus repository
        def nexusUrl = 'https://your-nexus-url'
        def nexusUsername = 'your-nexus-username'
        def nexusPassword = 'your-nexus-password'

        sh "${mvnHome}/bin/mvn deploy:deploy-file -Durl=${nexusUrl}/repository/releases/ -DrepositoryId=nexus-releases -DgroupId=your.group -DartifactId=your-artifact -Dversion=1.0 -Dpackaging=jar -Dfile=path/to/your/artifact.jar -DgeneratePom=true -DgeneratePom.description='Your artifact description'"
    }
}

def triggerAnotherJob() {
    stage('Trigger Another Jenkins Job') {
        // Trigger another Jenkins job
        build job: 'Your-Another-Jenkins-Job', parameters: [string(name: 'PARAM_NAME', value: 'PARAM_VALUE')]
    }
}

def sendEmailNotification() {
    stage('Send Email Notification') {
        emailext(
            to: 'recipient@example.com',
            subject: 'Build Status',
            body: '''<p>Build ${currentBuild.result}: ${env.BUILD_URL}</p>
                     <p>Project: ${env.JOB_NAME}</p>
                     <p>Branch: ${env.GIT_BRANCH}</p>''',
            mimeType: 'text/html',
            attachLog: true
        )
    }
}

pipeline {
    agent any

    environment {
        MVN_HOME = tool name: 'Maven', type: 'hudson.tasks.Maven$MavenInstallation'
    }

    stages {
        stage('Setup Tools') {
            steps {
                script {
                    setupTools()
                }
            }
        }

        stage('Build Pipeline') {
            steps {
                script {
                    checkoutCode()
                    buildAndTest()
                    runSonarQubeAnalysis()
                    deployToNexus()
                    triggerAnotherJob()
                    sendEmailNotification()
                }
            }
        }
    }
}
