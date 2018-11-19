-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 19 nov. 2018 à 21:31
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gestionetablissement`
--

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateEmbauche` date DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `profil_id` int(11) DEFAULT NULL,
  `etablissement_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4x4uoyx5v749t3354mmwtb3ts` (`profil_id`),
  KEY `FK_bd7coal48i922eeswbijptnof` (`etablissement_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`id`, `dateEmbauche`, `dateNaissance`, `email`, `nom`, `password`, `prenom`, `profil_id`, `etablissement_id`) VALUES
(1, '2018-11-13', '1985-11-23', 'user', 'محمد', 'e10adc3949ba59abbe56e057f20f883e', 'ابو شي حاجة', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `etablissement`
--

DROP TABLE IF EXISTS `etablissement`;
CREATE TABLE IF NOT EXISTS `etablissement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etablissement`
--

INSERT INTO `etablissement` (`id`, `nom`, `region`, `type`) VALUES
(1, 'مؤسسة المعتوهون', 'إقليم الرحامنة', 'خصوصية'),
(2, 'مؤسسة الإحسان', 'إقليم الرحامنة', 'خصوصية'),
(3, 'مؤسسة الوردة', 'إقليم الرحامنة', 'عمومية');

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cne` varchar(255) DEFAULT NULL,
  `dateNaissance` date DEFAULT NULL,
  `lieuNaissance` varchar(255) DEFAULT NULL,
  `niveauEtude` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `etablissement_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8df7gnuh5mr1tyioc1500hm9h` (`etablissement_id`)
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id`, `cne`, `dateNaissance`, `lieuNaissance`, `niveauEtude`, `nom`, `prenom`, `etablissement_id`) VALUES
(1, '93063740', '2018-11-16', 'مراكش', 'الاولى باكالوريا', 'كمال ', 'ولد تعربت', 1),
(2, '641652982', '1978-08-08', 'تحناوت', 'السادس اعدادي', 'عائشة', ' بامحمد', 2),
(3, '984294444', '1975-01-01', 'تحناوت', 'الاولى باكالوريا', 'فاطمة', 'فوقجدي', 2),
(4, '', '1980-09-06', 'اوريكة', 'السابعة اساسي', ' ابحري', 'عمر', 1),
(5, '', '1995-03-21', 'تحناوت', 'الثالثة اعدادي ', ' العبيد', 'احمد', 1),
(6, '', '1973-04-17', 'تحناوت', 'التاسعة اعدادي ', ' ايت أفقير', 'الحسين', 1),
(35, '', '1975-01-01', 'تحناوت', 'التاسعة اعدادي ', ' فوقجدي', 'فاطمة', 1),
(8, '', '1975-02-10', 'تمصلوحت', 'التاسعة اعدادي ', ' عنور', 'نزهة', 1),
(34, '', '1974-01-01', 'تحناوت', 'التاسعة اعدادي ', ' انضيف', 'محمد', 1),
(10, '', '1974-01-01', 'اوريكة', 'التاسعة اعدادي ', ' قلا', 'علي', 1),
(38, '9306374E7', '1976-02-03', 'مراكش', 'التالثة باكالوريا علوم تجريبية', ' ولد تعربت', 'كمال', 1),
(28, '', '1985-01-08', 'تحناوت', 'التامنة إعدادي', ' أيت أمزميز', 'حسن', 1),
(30, '', '1984-01-01', 'ايت بويحيا كيك', 'التامنة إعدادي', ' ايت ابرايم', 'نزهة', 1),
(39, '', '1978-08-08', 'تحناوت', 'التاسعة اعدادي ', ' بامحمد', 'عائشة', 1),
(40, '', '1976-04-18', 'تحناوت', 'التاسعة اعدادي ', ' ادريس البوحيتي', 'مولاي', 1);

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

DROP TABLE IF EXISTS `profil`;
CREATE TABLE IF NOT EXISTS `profil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `profil`
--

INSERT INTO `profil` (`id`, `code`, `libelle`) VALUES
(1, 'م1', 'استلد');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
