def clone(String wsItem, String branch, String url) {
    //def saItem = env.get("Sauce_Access")
    def branchItem = branch == null ? "master" : branch
    def reallyUrl = url.replaceAll("http://", "http://${env.Sauce_Access}@")
    sh """
        if [ -d "$wsItem" ]; then
            cd $wsItem && git reset --hard HEAD && git pull 
            echo "[INFO ] 更新代码完成 ..."
        else
            git clone $reallyUrl -b $branchItem $wsItem
            echo "[INFO ] 拉取代码完成 ..."
        fi
    """
    println("[DEBUG] $wsItem($branch) clone finish ... ")
}