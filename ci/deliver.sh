./gradlew :urlshortener-configuration:bootJar
docker login -u $DOCKER_USER -p $DOCKER_PASS
export TAG=`if [ "$BRANCH" == "master" ]; then echo "latest"; else echo $TRAVIS_BRANCH ; fi`
export REPO=sareyes/urlshortener-be
docker build -f Dockerfile -t $REPO:$COMMIT .
docker tag $REPO:$COMMIT $REPO:$TAG
docker tag $REPO:$COMMIT $REPO:travis-$TRAVIS_BUILD_NUMBER
docker push $REPO

cd ui
npm i
npm run-script build
export REPO=sareyes/urlshortener-fe
docker build -f Dockerfile -t $REPO:$COMMIT .
docker tag $REPO:$COMMIT $REPO:$TAG
docker tag $REPO:$COMMIT $REPO:travis-$TRAVIS_BUILD_NUMBER
docker push $REPO
