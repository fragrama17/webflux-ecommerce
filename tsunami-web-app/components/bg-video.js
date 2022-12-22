import styles from "../styles/Home.module.css";

export default () => {
    return (<div className={styles.video}>
        <video width="1920" height="1080" className="position-absolute" autoPlay muted loop>
            <source src="/surf-life-bg.mp4" type="video/mp4"/>
            Your browser does not support HTML5 video.
        </video>
    </div>);
}
