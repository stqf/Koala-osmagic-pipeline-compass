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
            cd $projectItem && git reset --hard HEAD && git pull 
            echo "[INFO ] 更新代码完成 ..."
        else
            git clone $reallyUrl -b $branchItem $projectItem
            echo "[INFO ] 拉取代码完成 ..."
        fi
    """
    println("[DEBUG] $projectItem($branch) clone finish ... ")
}