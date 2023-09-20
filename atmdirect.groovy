pipeline {

  options {
    ansiColor('xterm')
    timestamps ()
  }

//   environment {
//       my_git_token=credentials('github_token')
//       def application="graphql"
//       def application_repo="pikup-server"

//   }

  agent any 

  stages {


    stage ('Stage1 : compiling code') {
        steps {
            println "compiling mvn code"
            sleep 10
        }
    }

    stage ("Pushing docker image") {
        steps {
        println "pushing code to Docker hub"
        sleep 5
    }
    }

    stage ("Updating github for ArgoCD") {
     steps {
        dir ("atm"){
        sh "helm upgrade --install atm_demo . values.yaml" + " --set application.docker.tag=${currentBuild.number}"
     }
    }
  }

}
}
