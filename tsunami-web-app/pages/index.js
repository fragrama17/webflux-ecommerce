import styles from "../styles/Home.module.css";
import Image from "next/image";
import Head from "next/head";
import Sales from "../components/sales";
import BgVideo from "../components/bg-video";

export default function LandingPage() {

    return (
        <>
            <BgVideo/>
            <main className={styles.main}>
                <div className={styles.center}>
                    <Image className={styles.logo} src="/next.svg" alt="Next.js Logo" width={180} height={37} priority/>
                    <div className={styles.thirteen}>
                        <Image src="/thirteen.svg" alt="13" width={40} height={31} priority/>
                    </div>
                </div>
            </main>

            <Sales/>

        </>
    );
}
