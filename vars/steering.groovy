/*
* 调用失败, 不能包含steps块
 */
def exe(String item) {
    stage("任务A") {
        steps {
            echo "TRACE [任务A] start  ....  "
            echo "DEBUG [任务A] finish ....  "
        }
    }
}

def car(String item) {
    stage("任务A") {
        script {
            echo "TRACE [任务A] start  ....  "
            echo "DEBUG [任务A] finish ....  "
        }
    }
}

def bus(String item) {
    def items = ["任务A", "任务B", "任务C", "任务D"]
    for (String name : items) {
        stage(name) {
            script {
                echo "TRACE [$name] start  ....  "
                echo "DEBUG [$name] finish ....  "
            }
        }
    }
}

def bike(String item) {
    def tasks = [:]
    def items = ["紧急任务A", "紧急任务B", "紧急任务C", "紧急任务D"]
    for (String name : items) {
        def nameSwap = name;
        tasks."Nice-$nameSwap" = {
            stage(nameSwap) {
                script {
                    echo "TRACE [$nameSwap] start  ....  "
                    echo "params : $params"
                    echo "DEBUG [$nameSwap] finish ....  "
                }
            }
        }
    }
    parallel tasks
}

/*
* 调用失败, 不能包含stages、steps块
*/
def run(String item) {
    stages {
        stage("任务A") {
            steps {
                echo "TRACE [任务A] start  ....  "
                echo "DEBUG [任务A] finish ....  "
            }
        }
    }
}

/*
* 调用失败, 不能含有stages块
*/
def swing(String item) {
    stages {
        stage("任务B") {
            parallel {
                stage("功能一") {
                    steps {
                        echo "TRACE [任务B-功能一] start  ....  "
                        echo "DEBUG [任务B-功能一] finish ....  "
                    }
                }

                stage("功能二") {
                    stteps {
                        echo "TRACE [任务B-功能二] start  ....  "
                        echo "DEBUG [任务B-功能二] finish ....  "
                    }
                }

                stage("功能三") {
                    steps {
                        echo "TRACE [任务B-功能三] start  ....  "
                        echo "DEBUG [任务B-功能三] finish ....  "
                    }
                }
            }
        }

    }
}