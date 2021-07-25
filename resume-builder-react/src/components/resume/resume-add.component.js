import {withTranslation} from "react-i18next";
import {Helmet} from "react-helmet";
import "./resume.scss";
import {Component} from "react";

class ResumeAddComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            form:{
                options:{
                    menuItems: ['about.me', 'education', 'languages', 'experience', 'skills', 'websites.social.links', 'references']
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
                                {form.options.menuItems.map(menuItem =>
                                    <a key={menuItem} className={form.ui.selectedMenuItem === menuItem ? 'nav-link menu-item selected' : 'nav-link menu-item'} href={`#${menuItem}`} onClick={() => this.onSelectMenuItem(menuItem)}>{t(`resume.page.add.menu.item.${menuItem}.title`)}</a>
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
