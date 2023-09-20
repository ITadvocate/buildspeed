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
        steps {
            echo "compiling mvn code"
            sleep 10
        }
    }

    stage ("Pushing docker image") {
        steps {
        echo "pushing code to Docker hub"
        sleep 5
    }
    }

    stage ("Updating github for ArgoCD") {
     steps { 
        sh "helm upgrade --install atm_demo . values.yaml" + " --set application.docker.tag=${currentBuild.number}"
     }

  }

}
}
