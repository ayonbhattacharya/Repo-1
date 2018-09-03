"""
Created on Mon Sep 03 19:50:03 2018

@author: ab22490
"""
# Importing libraries 
from os.path import dirname, join

from bokeh.layouts import layout, widgetbox
from bokeh.models import ColumnDataSource, Div
from bokeh.models.widgets import DataTable, DateFormatter, TableColumn, Dropdown
from bokeh.palettes import RdYlBu3
from bokeh.plotting import figure, curdoc
from bokeh.layouts import column

import pandas as pd

# Defining Global Vars
file__glb=r'C:\ab22490\Work\Workspaces\PythonWorkspaces\Visual1\\'
sizing_mode = 'fixed'

# Reading and cleaning the data 

def get_params():
    print "In Params"
    country_selection = str(cntry_selector.value)
    scenario_selection = str(scenario_selector.value)
    print 'Country Selection' + country_selection
    print 'Scenario Selection ' + scenario_selection
    if country_selection == 'None' :
        country_selection = 'PH'
    if scenario_selection == 'None':
        scenario_selection = 'A4'
    return (country_selection, scenario_selection)
    
def update_data_on_dropdown():
    print "In Update"
    tuple_params = get_params()
    data_src = pd.ExcelFile('Data2.xlsx').parse(tuple_params[0] + ' - ' + tuple_params[1])
    #data_src = pd.read_csv('Data1.csv')
    bokeh_source = ColumnDataSource(data_src)
    return ColumnDataSource(data_src)


#Adding the elements --- Interactions

cntry_list= [("Phillippines", "PH"), ("Vietnam", "VN"), ("Australia", "AU")]
cntry_selector = Dropdown(label="Country Selector", button_type="primary", menu=cntry_list)

scenario_list = [("A4", "A4"), ("B1", "B1"), ("B3", "B3")]
scenario_selector = Dropdown(label="Scenario Selector", button_type="primary", menu=scenario_list)

# Defining Data Control Functions

controls = [cntry_selector, scenario_selector]
for control in controls:
    control.on_change('value', lambda attr, old, new: update_data_on_dropdown())

#Build Data

columns = [
        TableColumn(field="Rule", title="Activity Rules"),
        TableColumn(field="Account", title="Number of Accounts"),
        ]
data_table = DataTable(source=bokeh_source, columns=columns, width=400, height=280)
# Build Layout and Add to Document
input_controls = widgetbox(*controls, sizing_mode=sizing_mode)
desc = Div(text=open(join(dirname(file__glb), "init.html")).read(), width=800)

l = layout([
    [desc],
    [input_controls],
    [data_table],
], sizing_mode=sizing_mode)

update_data_on_dropdown()
curdoc().add_root(l)