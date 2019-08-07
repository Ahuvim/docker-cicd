job('NodeJS example') {
    scm {
        git('git://github.com/Ahuvim/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install")
    }
}

job('NodeJS Docker example') {
    scm {
        git('git://github.com/Ahuvim/docker-cicd.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs-new') 
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('ahuvim/maor-jb') //qa / dev
            buildContext('./basics')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('Ahuvim')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}

pipelineJob('boilerplate-pipeline') {



    def repo = "https://github.com/ahuvim/docker-cicd.git"

 

    triggers {

        scm('H/5 * * * *')

    }

    description("Pipeline for repo")

   

    definition {

        cpsScm{

            scm{

                git{

                    remote{

                        url('git://github.com/ahuvim/docker-cicd.git')

                        branches('master')

                        }

                    }

                }

            scriptPath("./basics/misc/Jenkinsfile")

            }

        }

    }