def package(String dirName){

    sh """
    echo "project is:$dirName"
    cd $WORKSPACE/$dirName
    mvn clean package
    """
}