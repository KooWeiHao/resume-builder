import {withTranslation} from "react-i18next";
import {Helmet} from "react-helmet";
import "./resume.scss";
import {Component} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

class ResumeAddComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            form:{
                options:{
                    menuItems: {'about.me': 'user-tie', 'education': 'university', 'languages': 'globe', 'experience': 'history', 'skills': 'tools', 'websites.social.links': 'link', 'references': 'user-tag'}
                },
                ui:{
                    selectedMenuItem: "about.me"
                }
            }
        };
    }

    onSelectMenuItem = (menuItem) =>{
        const {ui} = this.state.form;
        ui.selectedMenuItem = menuItem;
        this.setState({ui});
    }

    render(){
        const {t} = this.props;
        const {form} = this.state;

        return (
            <>
                <Helmet>
                    <title>{t("resume.page.title.add")}</title>
                </Helmet>

                <div className="container-lg pt-4">
                    <div className="row">
                        <div className="col-3">
                            <nav className="nav flex-column menu">
                                {Object.keys(form.options.menuItems).map(title =>
                                    <a key={title} className={form.ui.selectedMenuItem === title ? 'nav-link menu-item selected' : 'nav-link menu-item'} href={`#${title}`} onClick={() => this.onSelectMenuItem(title)}>
                                        <span className="pr-2"><FontAwesomeIcon icon={form.options.menuItems[title]}/></span> {t(`resume.page.add.menu.item.${title}.title`)}
                                    </a>
                                )}
                            </nav>
                        </div>

                        <div className="col-9">

                        </div>
                    </div>
                </div>
            </>
        );
    }
}

export default withTranslation()(ResumeAddComponent);
