<a id="readme-top"></a>
<!-- PROJECT LOGO -->
<br />
<div align="center">

  <h1 align="center">Solar Watch</h1>

</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

  Solar Watch is a full-stack weather web application where registered users can retrieve sunrise and sunset times for any city on any date. The app uses an integrated PostgreSQL database to store every requested city and solar event time to reduce external API calls, increasing performance. If the requested data is not yet available in the database, only then does the app retrieve it from external APIs.


### Built With

* **Frontend**

  [![JavaScript]][JavaScript-url]
  [![React.js]][React-url]
  [![Vite.js]][Vite-url]

* **Backend**

  [![Java]][Java-url]
  [![Spring-Boot]][Spring-Boot-url]
  [![Maven]][Maven-url]

* **Database**

  [![PostgreSQL]][PostgreSQL-url]

* **Containerization**

  [![Docker]][Docker-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* [Docker][Docker-url] installed
* An OpenWeather API key is required. You can <a href="https://home.openweathermap.org/users/sign_up">sign up here</a> for free.

### Installation

* **Please note:** The app is not in a working condition yet, so the installation steps are missing for now.

* You can clone the repository to look at the code in the meantime:

  ```bash
  git clone https://github.com/CodecoolGlobal/solar-watch-MVP-java-Davidjuhasz05.git
  cd solar-watch-MVP-java-Davidjuhasz05
  ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- ROADMAP -->
## Roadmap

- [x] Build the Backend
    - [x] Add Database Connection
    - [x] Dockerize Backend
    - [x] Implement Token-Based User Auth. with Spring Security (Login, Signup)
    - [x] Add Admin Role and CRUD Functionality
- [ ] Build the Frontend
    - [ ] Create Necessary Pages (Login/Signup, Home, Results)
    - [ ] Connect to Backend
    - [ ] Create CSS
    - [ ] Dockerize Frontend
- [ ] Create Tests (Unit, Integration)
- [ ] Add CI/CD pipeline
- [ ] Expand with New Features

See the [open issues](https://github.com/CodecoolGlobal/solar-watch-MVP-java-Davidjuhasz05/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [OpenWeatherMap API](https://openweathermap.org/api/geocoding-api) for converting city names into geographical coordinates
* [Sunrise-Sunset API](https://sunrise-sunset.org/api) for the sunrise/sunset times

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05.svg?style=for-the-badge
[contributors-url]: https://github.com/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05.svg?style=for-the-badge
[forks-url]: https://github.com/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05/network/members
[stars-shield]: https://img.shields.io/github/stars/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05.svg?style=for-the-badge
[stars-url]: https://github.com/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05/stargazers
[issues-shield]: https://img.shields.io/github/issues/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05.svg?style=for-the-badge
[issues-url]: https://github.com/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05/issues
[license-shield]: https://img.shields.io/github/license/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05.svg?style=for-the-badge
[license-url]: https://github.com/Davidjuhasz05/solar-watch-MVP-java-Davidjuhasz05/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/david-juhasz-dave
[product-screenshot]: images/screenshot.png
<!-- Shields.io badges. You can a comprehensive list with many more badges at: https://github.com/inttter/md-badges -->
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
[JavaScript]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=000
[JavaScript-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://react.dev
[Vite.js]: https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white
[Vite-url]: https://vite.dev
[React-Router.js]: https://img.shields.io/badge/React_Router-CA4245?style=for-the-badge&logo=react-router&logoColor=white
[React-Router-url]: https://reactrouter.com
[Spring-Boot]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-Boot-url]: https://spring.io/projects/spring-boot
[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://www.oracle.com/java
[Maven]: https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org
[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org
[Docker]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com
[Kubernetes]: https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white
[Kubernetes-url]: https://kubernetes.io
[kubectl-url]: https://kubernetes.io/docs/tasks/tools
[Terraform]: https://img.shields.io/badge/Terraform-7B42BC?style=for-the-badge&logo=terraform&logoColor=white
[Terraform-url]: https://developer.hashicorp.com/terraform
[Terraform-download-url]: https://developer.hashicorp.com/terraform/downloads
[Helm]: https://img.shields.io/badge/Helm-0F1326?style=for-the-badge&logo=helm&logoColor=white
[Helm-url]: https://helm.sh
[Helm-install-url]: https://helm.sh/docs/intro/install
[AWS-CLI-url]: https://aws.amazon.com/cli
