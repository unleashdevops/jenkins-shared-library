def call(body) {
def config = [:]
boolean skip_ci=false
body.resolveStrategy = Closure.DELEGATE_FIRST
body.delegate = config
body()
 node( label: 'linux'){
       String commit_message = sh(
          script: "git log -1 --pretty=%B",
          returnStdout: true
       ) 
      skip_ci=commit_message.contains("[skipci]")
     
  if(skip_ci){
      println """
                [skipci] token FOUND in commit message: $commit_message 
                Skipping pipeline...
             """
          return
  } else{
        println """
                [skipci] token NOT FOUND in commit message: $commit_message
                Running pipeline...
                """
         return 
         }
   }

 if(!skip_ci){
 pipeline {
     agent { label 'linux' }
     options {
      buildDiscarder(logRotator(numToKeepStr: '20'))
      skipDefaultCheckout()
       }
       stages {
            stage ('Clean Workspace') {
                steps {
                    script {
                        sh 'git clean -dxf'
                    }
                }
            }
          
          stage('test'){
            steps{
              echo "test"
            }
          }
        }
      }
  }
}
