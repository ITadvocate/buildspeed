pipeline {

  options {
    ansiColor('xterm')
    timestamps ()
  }

  environment {
      def BUILD_NUMBER_VAL="${BUILD_NUMBER_VAL}"
  }

  agent any

  stages {

    stage ('Stage1 : compiling code') {
        steps {
            println "compiling mvn code"
            sleep 1
        }
    }

    stage ("Pushing docker image") {
        steps {
        println "pushing code to Docker hub"
        sleep 1
    }
    }

    stage ("Updating github for ArgoCD") {
     steps {
        dir ('atm') {
        sh"cat demo_values.yaml > values.yaml"
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
                                        sh "git checkout master"
                                        sh "git add values.yaml"                                        
                                        sh "git config --global --add safe.directory '*'"
                                        sh "git commit -m 'pushing test build: ${BUILD_NUMBER_VAL}'"
                                        sh "git push -u origin master"
                                    }
                                }
                            }
                        }
                    }

    stage ('Clean') {
        steps {
            sh"rm -rf ${WORKSPACE}/atm"
        }
    }


}
}
