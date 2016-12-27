#!groovy?

def call(text, channel) {
    def slackURL = 'https://hooks.slack.com/services/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'
    def payload = JsonOutput.toJson([text  : text,
                                    channel   : channel,
                                    username  : "jenkins",
                                    icon_emoji: ":jenkins:"])
    sh "curl -X POST --data-urlencode \'payload=${payload}\' ${slackURL}"
}

