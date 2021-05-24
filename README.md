# GithubViewer App

## What does it do?

This app allows you to search for users' GitHub repositories. You can also view details such as:

- recent contributors
- stars, forks, viewers
- or just visit GitHub page

## Technologies

App uses MVVM pattern and is heavily dependent on LiveData. Other cool used things are:

- Navigation Component
- Dependency Injection with Hilt
- Paging 3

### Used 3rd party libraries

- Glide
- Retrofit

## Future

Things that could be improved:

- caching data. In case of Paging 3, it is possible for example with usage of `RemoteMediator`,
  but it is still *experimental api*
- more tests...