#!/usr/bin/env bash
set -e
set -o pipefail
set -x

function package() {
    echo "Packaging..."
    ./mvnw clean package -DskipTests
}

function help_msg() {
    echo "Usage: manage.sh [package]"
}

function build_image() {
  TAG_NAME="$1"
  echo "Extracting jar file to layers... Tag Name: ${TAG_NAME}"
  java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted
  echo "Building docker image..."
  docker build . -t "${TAG_NAME}"
  IMG_ID=$(docker images -q "${TAG_NAME}")
  echo "Image built successfully. Image ID: ${IMG_ID}"
}

function push_image() {
  TAG_NAME="$1"
  echo "Pushing image to Aliyun docker registry... Tag Name: ${TAG_NAME}"
  IMG_ID=$(docker images -q "${TAG_NAME}")
  echo "Image ID: ${IMG_ID}"
  docker tag "${IMG_ID}" "registry.cn-hangzhou.aliyuncs.com/pmrfansub/${TAG_NAME}"
  docker push "registry.cn-hangzhou.aliyuncs.com/pmrfansub/${TAG_NAME}"
  echo "Image pushed successfully."
}

case $1 in
    "package")
        package
        ;;
    "build")
        build_image "$2"
        ;;
    "push")
        push_image "$2"
        ;;
    "build-and-push")
        package
        build_image "$2"
        push_image "$2"
        ;;
    *)
        help_msg
        ;;
esac
