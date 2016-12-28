#!groovy?
def call(body) {

  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  env.BRANCH_NAME = config.branchName
  // git branch: config.branchName, credentialsId: config.credentialsId, url:"https://${config.url}" 
  checkout scm

  sh '''
    git config user.name 'ci.infra'
    git config user.email 'ci.infra@ironsource.com'
    git checkout staging
    git merge ${BRANCH_NAME}
    echo env.GIT_COMMIT=$(git rev-parse HEAD) > merge.properties
    echo env.BRANCH_NAME=staging >> merge.properties
    sed 's/$/"/g' -i merge.properties
    sed 's/=/="/g' -i merge.properties
  '''

  load ('merge.properties')
}
