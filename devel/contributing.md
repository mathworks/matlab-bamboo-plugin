## Contributing
To get started, install the [Atlassian SDK](https://developer.atlassian.com/server/framework/atlassian-sdk/install-the-atlassian-sdk-on-a-linux-or-mac-system/):

```
brew tap atlassian/tap
brew install atlassian/tap/atlassian-plugin-sdk
```

Verify changes by running tests and building locally with the following command:

```
atlas-mvn package
```

## Creating a New Release

Familiarize yourself with the best practices for [releasing and maintaining GitHub actions](https://docs.github.com/en/actions/creating-actions/releasing-and-maintaining-actions).

Changes should be made on a new branch. The new branch should be merged to the main branch via a pull request. Ensure that all of the CI pipeline checks and tests have passed for your changes.

After the pull request has been approved and merged to main, follow the Github process for [creating a new release](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository). The release must follow semantic versioning (ex: vX.Y.Z). This will kick off a new pipeline execution, and the plugin will automatically be uploaded to the new release as a release asset if the pipeline finishes successfully.

