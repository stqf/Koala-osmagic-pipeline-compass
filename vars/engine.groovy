def compile(String item) {

    if ("Web" == item) {
        echo "[DEBUG] compile Web  ... "
    } else if ("Java" == item) {
        echo "[DEBUG] compile Java ... "
    } else {
        String stageName = "Compile-$item-in-node"
        stage(stageName) {
            agent {
                docker {
                    image 'maven:3-alpine'
                    label "${env.labelItem}"
                }
            }
            script {
                echo "[TRACE] [$stageName] start  ....  "
                echo "[DEBUG] [$stageName] finish ....  "
            }
        }
    }

    echo "[INFO ] compile $item finish"
}


def compiles(List<String> items) {

    def tasks = [:]

    for (String item : items) {
        String taskName = "Compile-$item"
        tasks."$taskName" = {
            compile(taskName)

            echo "[INFO ] compile finish ... "
        }
    }

    parallel tasks

}