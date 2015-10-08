/**
 * Downy is a Groovy script that one can use to download 100s of files.
 *
 *      TODO: make downloading an asynchronous process
 *
 * @author Jatinder Singh on 07, October 2015
 */
class Downy {
    static void main(String[] args) {
        println "Running Downy. This can take few minutes. Please wait.."

        if(args != null && args.length > 0) {
            DownyDownloader downloader = new DownyDownloader()
            downloader.download(args)

            println "Downy is done!"
        } else {
            println "<ERROR> Wrong # of arguments passed. Please run like below:"
            println "\t\t groovy Downy.groovy FOLDER_NAME URLS_FILE_LOCATION"
        }
    }
}

class DownyDownloader implements Download {

    @Override
    void download(String[] args) {
        String urlsFileLocation = args[0]
        String downloadFolderLocation = args[1]

        def downloadFolder = new File(downloadFolderLocation)
        if(!downloadFolder.exists()) { downloadFolder.mkdirs() }

        File urlsFile = new File(urlsFileLocation)

        urlsFile.eachLine { url ->
            String fileName = url.substring(url.lastIndexOf("/") + 1)
            String fullFileLocation = downloadFolderLocation + "/" + fileName
            println "\tDownloading " + url + ", as " + fileName
            def file = new File(fullFileLocation).newOutputStream()

            try {
                file << new URL(url).openStream()
            } catch (Throwable e) {
                println e.getMessage()
            } finally {
                file.close()
            }
        }
    }
}

interface Download {
    void download(String[] args)
}