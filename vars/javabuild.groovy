def package(String dirName){

    sh """
    cd $WORKSPACE/$dirName
    mvn clean package
    """
}