#!/usr/bin/env bash
set -e

VERSION=$1
MESSAGE=$2

if [ -z "$VERSION" ]; then
  echo "Usage: ./release.sh <version> [message]"
  exit 1
fi

TAG="v$VERSION"

if ! [[ "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
  echo "❌ Invalid version format. Use MAJOR.MINOR.PATCH"
  exit 1
fi

if git rev-parse "$TAG" >/dev/null 2>&1; then
  echo "❌ Tag $TAG already exists"
  exit 1
fi

LATEST_TAG=$(git tag -l 'v*' \
  | sed 's/^v//' \
  | grep -E '^[0-9]+\.[0-9]+\.[0-9]+$' \
  | sort -V \
  | tail -n 1)

if [ -n "$LATEST_TAG" ]; then
  if [ "$(printf '%s\n' "$LATEST_TAG" "$VERSION" | sort -V | head -n1)" != "$LATEST_TAG" ]; then
    echo "❌ Version $VERSION is not greater than latest tag v$LATEST_TAG"
    exit 1
  fi
fi

if [ -z "$MESSAGE" ]; then
  MESSAGE=$(git log -1 --pretty=%B)
fi

if ! git diff-index --quiet HEAD --; then
  echo "❌ Working tree is not clean"
  exit 1
fi

git tag -a "$TAG" -m "$MESSAGE"
git push origin "$TAG"

echo "✅ Created tag $TAG"
echo "📝 Message:"
echo "$MESSAGE"
