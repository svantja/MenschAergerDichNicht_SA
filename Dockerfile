FROM hseeberger/scala-sbt:8u151-2.12.4-1.1.1
LABEL desctiption = "Mensch Aerger Dich Nicht packed into docker"

RUN apt-get -y install fish vim
RUN git clone https://github.com/svantja/MenschAergerDichNicht_SA.git
WORKDIR /root/de.htwg.se.MenschAergerDichNicht_SA
RUN sbt compile
COPY . MenschAergerDichNicht_SA/
WORKDIR /root/MenschAergerDichNicht_SA/
EXPOSE 8080
CMD sbt run