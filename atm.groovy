pipeline {

  options {
    ansiColor('xterm')
    timestamps ()
  }

  environment {
      my_git_token=credentials('github_token')
      def application="graphql"
      def application_repo="pikup-server"

  }

  agent {
    kubernetes {
      yamlFile 'kaniko.yaml'
    }

  }

  stages {


    stage ('Stage1 : compiling code') {
            println "compiling mvn code"
            sh "sleep 10"

    }

    stage ("Pushing docker image") {
        printl "pushing code to Docker hub"
        sh "sleep 5"
    }

    stage ("Updating github for ArgoCD")
     sh "helm upgrade --install atm_demo . values.yaml" + " --set application.docker.tag=${currentBuild.number}"

  }

}
