/**
 * GIT拉取代码
 * @param wsItem 工作路径
 * @param projectItem 项目
 * @param branch 分支
 * @param url 代码地址
 * @return
 */
def clone(String wsItem, String projectItem, String branch, String url) {
    //def saItem = env.get("Sauce_Access")
    def branchItem = branch == null ? "master" : branch
    def reallyUrl = url.replaceAll("http://", "http://${env.Sauce_Access}@")
    sh """
        cd $wsItem
        if [ -d "$projectItem" ]; then
            cd $projectItem && git reset --hard HEAD && git checkout $branchItem
            echo "[INFO ] 更新代码完成 ..."
        else
            git clone $reallyUrl -b $branchItem $projectItem
            echo "[INFO ] 拉取代码完成 ..."
        fi
    """

    if (isBranch(branchItem, projectItem, wsItem)) {
        sh "cd $wsItem && cd $projectItem && git pull"
    }

    println("[DEBUG] $projectItem($branch) clone finish ... ")
}

/**
 * 判断是分支还是TAG
 * @param branch 分支名称
 * @param project 项目目录
 * @return
 */
def isBranch(String branch, String project, String wsItem) {
    String[] branchItems = sh(script: """cd $wsItem && cd $project && git branch""", returnStdout: true).trim().split("\n")
    return Arrays.asList(branchItems).contains(branch)
}