#!groovy?
def call(body) {

  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  echo config.branchName
  echo config.credentialsId
  echo config.newCred
  echo config.url

  // git branch: config.branchName, credentialsId: config.credentialsId, url:"https://${config.url}" 
  // checkout scm

  withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: config.credentialsId, usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {

    sh '''
      git config user.name 'ci.infra'
      git config user.email 'ci.infra@ironsource.com'
      git fetch
      git checkout staging
      git merge ${BRANCH_NAME}
      echo env.GIT_COMMIT=$(git rev-parse HEAD) > merge.properties
      echo env.BRANCH_NAME=staging >> merge.properties
      sed 's/$/"/g' -i merge.properties
      sed 's/=/="/g' -i merge.properties
    '''
  }

  load ('merge.properties')
}
