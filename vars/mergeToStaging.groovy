#!groovy?
def call(body) {

  echo "in github merge"
  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  echo "passed body parsing"

        // git config user.name 'ci.infra'
        // git config user.email 'ci.infra@ironsource.com'
        // git checkout staging
        // git merge ${config.branchName}
        // echo env.GIT_COMMIT=$(git rev-parse HEAD) > merge.properties
        // echo env.GIT_BRANCH=staging >> merge.properties
        // sed 's/$/"/g' -i merge.properties
        // sed 's/=/="/g' -i merge.properties
    // load ('merge.properties')
}
