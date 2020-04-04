
// Path where the docToolchain will produce the output files.
// This path is appended to the docDir property specified in gradle.properties
// or in the command line, and therefore must be relative to it.
outputPath = 'build'

inputPath = '.'

inputFiles = [
        [file: 'arc42-template.adoc', formats: ['html','pdf','docbook']],
        [file: 'ppt/Demo.pptx.ad', formats: ['revealjs']]
             ]

taskInputsDirs = ["${inputPath}/src",
                  "${inputPath}/images",
                 ]

taskInputsFiles = ["${inputPath}/arc42-template.adoc"]

confluence = [:]
confluence.with {
    input = [[ file: "build/html5/arc42-template.html", ancestorId: '360466']]
    ancestorId = '360466'
    api = 'https://openwms.atlassian.net/wiki/rest/api/'
    spaceKey = 'OPENWMS'
    createSubpages = false
    pagePrefix = ''
    preambleTitle = 'Technical Architecture'
    pageSuffix = ''
    credentials = "${System.getenv('ATLASSIAN_USER')}:${System.getenv('ATLASSIAN_PASSWORD')}".bytes.encodeBase64().toString()
//    extraPageContent = '<ac:structured-macro ac:name="warning"><ac:parameter ac:name="title" /><ac:rich-text-body>This is a generated page, do not edit!</ac:rich-text-body></ac:structured-macro>'
}


github = [:]
github.with {
    user = "${System.getenv('GITHUB_USER')}"
    password = "${System.getenv('GITHUB_PASSWORD')}"
    root = "https://api.github.com/"
    organization = "openwms"
    repository = "org.openwms"
    resultsPerPage = 100
}