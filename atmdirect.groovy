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
        dir ('atm') {
        sh"cat demo_values > values.yaml"
        sh """sed -i "s/v1/v${BUILD_NUMBER}/" values.yaml"""
        }
     }
  }



    stage ('Git-Commit push ****************') {
                    steps {
                            dir ('atm') {
                                script {
                                    withCredentials([gitUsernamePassword(credentialsId: 'GITHUB_TOKEN', gitToolName: 'Default')]) {
                                        sh "git config --global user.email 'vikram@test.com'"
                                        sh "git config --global user.name 'VicK'"
                                        sh "git add values.yaml"
                                        sh "git config --global --add safe.directory '*'"
                                        sh "git pull --set-upstream origin master"
                                        sh "git commit -m 'pushing test build: ${BUILD_NUMBER}"
                                        sh "git pull --set-upstream origin master"
                                        sh "git branch"
                                        sh "git push --set-upstream origin -f ${groovy_branch}"
                                    }
                                }
                            }
                        }
                    }
                



}
}
